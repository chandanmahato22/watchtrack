package in.chandan.WatchTrack.library.service;

import in.chandan.WatchTrack.auth.service.AuthService;
import in.chandan.WatchTrack.catalog.entity.Episode;
import in.chandan.WatchTrack.catalog.entity.Season;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.EpisodeRepository;
import in.chandan.WatchTrack.catalog.repository.SeasonRepository;
import in.chandan.WatchTrack.catalog.repository.TitleRepository;
import in.chandan.WatchTrack.catalog.service.TitleService;
import in.chandan.WatchTrack.enums.LibraryStatus;
import in.chandan.WatchTrack.enums.Provider;
import in.chandan.WatchTrack.excepitons.BadRequestException;
import in.chandan.WatchTrack.excepitons.ResourceNotFoundException;
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
            title = titleService.saveMovie(request.getExternalId());
        }else title = titleService.saveTv(request.getExternalId());

        var existing = libraryRepository.findByUserAndTitle(user,title);
        if(existing.isPresent()){
            return existing.get();
        }

        UserLibrary library = new UserLibrary();

        library.setStatus(LibraryStatus.valueOf(request.getStatus()));
        library.setTitle(title);

        if("TV".equals(request.getType())) {
            if(!validateProgress(title, request.getSeason(), request.getEpisode())){
                throw new BadRequestException("season : " + request.getSeason() +
                        " or episode :" + request.getEpisode() +
                        "doesn't exits for " + title.getName());
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
                () -> new ResourceNotFoundException("title not found")
        );

        UserLibrary library = libraryRepository.findByUserAndTitle(user, title).orElseThrow(
                () -> new ResourceNotFoundException("Library entry not found")
        );
        LibraryStatus status;
        try{
            status = LibraryStatus.valueOf(req.getStatus());
        }
        catch(IllegalArgumentException | NullPointerException e){
            throw new BadRequestException("Invalid library status : " + req.getStatus());
        }

        library.setStatus(status);

        return LIbraryMapper.toLibraryData(library);
    }

    @Transactional
    public UserLibrary updateProgress(
            Long id,
            UpdateProgressRequest req){
            User user = authService.getCurrentUser();
            UserLibrary library = libraryRepository.findByIdAndUser(id,user).orElseThrow(
                    () -> new ResourceNotFoundException("library item not found")
            );
            Title title = library.getTitle();

            if(title.getType() == null || String.valueOf(title.getType()).equals("MOVIE")){
                throw new BadRequestException("watch progress by season and episode " +
                        "only available for tv shows. ");
            }
            Integer seasonNumber = req.getCurrentSeason();
            Integer episodeNumber = req.getCurrentEpisode();

            if(!validateProgress(title, seasonNumber,episodeNumber)){
                throw new BadRequestException("season : " + seasonNumber +
                        " or episode :" +episodeNumber +
                        "doesn't exits for " + title.getName());
            }
            library.setCurrentEpisode(req.getCurrentEpisode());
            library.setCurrentSeason(req.getCurrentSeason());

            return library;
    }

    private boolean validateProgress(
            Title title,
            Integer seasonNumber,
            Integer episodeNumber) {

        Season season = seasonRepository
                .findByTitleAndSeasonNumber(title, seasonNumber)
                .orElse(null);

        if (season == null) {
            return false;
        }
        Episode episode = episodeRepository
                .findBySeasonAndEpisodeNumber(season, episodeNumber)
                .orElse(null);

        if (episode == null) {
            return false;
        }
        return true;
    }

}
