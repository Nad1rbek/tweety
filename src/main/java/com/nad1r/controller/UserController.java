package com.nad1r.controller;

import com.nad1r.request.UpdateUserRequest;
import com.nad1r.service.FileService;
import com.nad1r.service.LikeService;
import com.nad1r.service.TweetService;
import com.nad1r.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TweetService tweetService;
    private final FileService fileService;
    private final LikeService likeService;


    @GetMapping
    public ResponseEntity<?> getUsers(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page){
        return ResponseEntity.ok(userService.getUsers(page));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable long id,
                                            @Valid @RequestBody UpdateUserRequest request){
        return ResponseEntity.ok(userService.updateUserById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/tweets")
    ResponseEntity<Page<?>> getUsersTweetsByUserId(@PathVariable Long id,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(tweetService.getTweetsByUserId(id, page));
    }

    @GetMapping("/{id}/likes")
    ResponseEntity<Page<?>> getUsersLikesByUserId(@PathVariable Long id,
                                                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
        return ResponseEntity.status(200).body(likeService.getLikesByUserId(id, page));
    }

    @PostMapping(
            value = x"/{username}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> uploadUserProfileImage(@PathVariable String username,
                                                @RequestParam("file") MultipartFile file) {
        fileService.uploadProfileImage(username, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/image/download")
    ResponseEntity<byte[]> downloadUserProfileImage(@PathVariable String username) {
        return ResponseEntity.ok(fileService.downloadProfileImage(username));
    }
}
