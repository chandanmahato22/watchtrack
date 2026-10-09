package in.chandan.WatchTrack.library.service;

import in.chandan.WatchTrack.auth.service.AuthService;
import in.chandan.WatchTrack.catalog.entity.Title;
import in.chandan.WatchTrack.catalog.repository.TitleRepository;
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

    @Autowired
    private AuthService authService;

    public LibraryService(LibraryRepository libraryRepository,
                          TitleService titleService,
                          UserRepository userRepository,
                          TitleRepository titleRepository){
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
        this.titleService = titleService;
        this.titleRepository = titleRepository;
    }


    public UserLibrary addToLibrary(AddToLibraryRequest request){

        User user = authService.getCurrentUser();

        UserLibrary library = new UserLibrary();
        library.setStatus(LibraryStatus.valueOf(request.getStatus()));
        library.setTitle(titleService.saveMovie(request.getExternalId()));
        library.setCurrentSeason(request.getSeason());
        library.setCurrentEpisode(request.getEpisode());
        library.setUser(user);

        libraryRepository.save(library);
        return library;
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
            library.setCurrentEpisode(req.getCurrentEpisode());
            library.setCurrentSeason(req.getCurrentSeason());
            return library;
    }
}
