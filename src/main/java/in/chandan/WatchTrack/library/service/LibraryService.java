package in.chandan.WatchTrack.library.service;

import in.chandan.WatchTrack.auth.service.AuthService;
import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.EpisodeRepository;
import in.chandan.WatchTrack.catalog.repository.SeasonRepository;
import in.chandan.WatchTrack.catalog.repository.TitleRepository;
import in.chandan.WatchTrack.catalog.service.SeasonService;
import in.chandan.WatchTrack.catalog.service.TitleService;
import in.chandan.WatchTrack.enums.LibraryStatus;
import in.chandan.WatchTrack.enums.Provider;
import in.chandan.WatchTrack.library.dto.AddToLibraryRequest;
import in.chandan.WatchTrack.library.dto.LibraryData;
import in.chandan.WatchTrack.library.dto.UpdateProgressRequest;
import in.chandan.WatchTrack.library.entity.UserLibrary;
import in.chandan.WatchTrack.library.mapper.LIbraryMapper;
import in.chandan.WatchTrack.library.repository.LibraryRepository;
import in.chandan.WatchTrack.users.entity.User;
import in.chandan.WatchTrack.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private LibraryRepository libraryRepository;
    private TitleService titleService;
    private UserRepository userRepository;
    private TitleRepository titleRepository;
    private final SeasonRepository seasonRepository;
    private final EpisodeRepository episodeRepository;
    @Autowired
    private AuthService authService;

    public LibraryService(LibraryRepository libraryRepository,
                          TitleService titleService,
                          UserRepository userRepository,
                          TitleRepository titleRepository, SeasonRepository seasonRepository, EpisodeRepository episodeRepository){
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
        this.titleService = titleService;
        this.titleRepository = titleRepository;
        this.seasonRepository = seasonRepository;
        this.episodeRepository = episodeRepository;
    }


    public UserLibrary addToLibrary(AddToLibraryRequest request){

        User user = authService.getCurrentUser();
        Title title;

        if("MOVIE".equals(request.getType())){
            title = titleService.saveTv(request.getExternalId());
        }else title = titleService.saveTv(request.getExternalId());

        return libraryRepository.findByIdAndUser(request.getExternalId(), user).orElseGet(
                () -> {
                    UserLibrary library = new UserLibrary();

                    library.setStatus(LibraryStatus.valueOf(request.getStatus()));
                    library.setTitle(title);
                    library.setStatus(LibraryStatus.valueOf(request.getStatus()));
                    if("TV".equals(request.getType())) {
                        if(!validateProgress(title, request.getSeason(), request.getEpisode())){
                            throw new IllegalArgumentException("invalid seasonNumber or EpisodeNumber");
                        }
                        library.setCurrentSeason(request.getSeason());
                        library.setCurrentEpisode(request.getEpisode());
                    }else {
                        library.setCurrentEpisode(null);
                        library.setCurrentSeason(null);
                    }
                    library.setUser(user);

                    return libraryRepository.save(library);
                }
        );
    }

    public List<LibraryData> getLibraryData(){
       User user = authService.getCurrentUser();
        List<LibraryData> libraries =  libraryRepository.findByUser(user).stream()
                .map(LIbraryMapper::toLibraryData).toList();

        return libraries;
    }

    @Transactional
    public LibraryData updateStatus(AddToLibraryRequest req){
        User user = authService.getCurrentUser();
        Title title = titleRepository.findByExternalIdAndProvider(req.getExternalId(),
                                                                  Provider.TMDB).orElseThrow(
                () -> new RuntimeException("Title not found")
        );

        UserLibrary library = libraryRepository.findByUserAndTitle(user, title).orElseThrow(
                () -> new RuntimeException("Library entry not found")
        );

        library.setStatus(LibraryStatus.valueOf(req.getStatus()));

        return LIbraryMapper.toLibraryData(library);
    }

    @Transactional
    public UserLibrary updateProgress(
            Long id,
            UpdateProgressRequest req){
            User user = authService.getCurrentUser();
            UserLibrary library = libraryRepository.findByIdAndUser(id,user).orElseThrow(
                    () -> new RuntimeException("library item not found")
            );
            Title title = library.getTitle();

            if(title.getType() == null || String.valueOf(title.getType()).equals("MOVIE")){
                throw new IllegalArgumentException("watch progress by season and episode " +
                        "only available for tv shows. ");
            }
            Integer seasonNumber = req.getCurrentSeason();
            Integer episodeNumber = req.getCurrentEpisode();

//            Season season = seasonRepository.findByTitleAndSeasonNumber(title,seasonNumber).orElseThrow(
//                    () -> new IllegalArgumentException("season " + seasonNumber +
//                            " has not been synced for this title")
//            );
//
//            Episode episode = episodeRepository
//                    .findBySeasonAndEpisodeNumber(season,episodeNumber).orElseThrow(
//                            () -> new IllegalArgumentException("episode " + episodeNumber + " not aired yet")
//                    );
            if(!validateProgress(title, seasonNumber,episodeNumber)){
                throw new IllegalArgumentException("invalid seasonNumber or EpisodeNumber");
            }
            library.setCurrentEpisode(req.getCurrentEpisode());
            library.setCurrentSeason(req.getCurrentSeason());

            return library;
    }

    private boolean validateProgress(Title title,Integer seasonNumber , Integer episodeNumber) {
        if(seasonNumber == null || seasonNumber < 1 || episodeNumber == null || episodeNumber < 1) return false;
        Season season = seasonRepository.findByTitleAndSeasonNumber(title, seasonNumber).orElse(null);
        if(season == null) return false;
        Episode episode = episodeRepository
                .findBySeasonAndEpisodeNumber(season, episodeNumber).orElse(null);
       return episode != null;
    }

}
