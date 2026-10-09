package in.chandan.WatchTrack.library.controller;


import in.chandan.WatchTrack.library.dto.AddToLibraryRequest;
import in.chandan.WatchTrack.library.dto.LibraryData;
import in.chandan.WatchTrack.library.dto.UpdateProgressRequest;
import in.chandan.WatchTrack.library.entity.UserLibrary;
import in.chandan.WatchTrack.library.service.LibraryService;
import in.chandan.WatchTrack.users.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/library")
public class LibraryController {

    private LibraryService libraryService;

    public LibraryController(LibraryService libraryService){
        this.libraryService = libraryService;
    }

    @PostMapping("/add")
    public ResponseEntity<UserLibrary> addMovie(@RequestBody AddToLibraryRequest libReq){
            UserLibrary library = libraryService.addToLibrary(libReq);
            return ResponseEntity.ok(library);
    }

    @GetMapping
    public ResponseEntity<List<LibraryData>> getLibraryData(){
        return ResponseEntity.ok(libraryService.getLibraryData());
    }

    @PutMapping("/setStatus")
    public ResponseEntity<LibraryData> updateStatus(@RequestBody AddToLibraryRequest libReq){
            return ResponseEntity.ok(libraryService.updateStatus(libReq));
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<UserLibrary> updateProgress(@PathVariable Long id,
                                                      @RequestBody UpdateProgressRequest req){
        return ResponseEntity.ok(libraryService.updateProgress(id,req));
    }
}
