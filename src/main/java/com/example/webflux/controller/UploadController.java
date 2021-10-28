package com.example.webflux.controller;

import com.example.webflux.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/upload")
@RequiredArgsConstructor
public class UploadController {

    @Autowired
    private UploadService uploadService;


    // use Flux<FilePart> for multiple file upload
    @PostMapping(value = "/upload-flux-save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<String> updoadSave(@RequestPart("file") FilePart filePart){
        return uploadService.saveFileAndReturnBase64(filePart);
    }
    // use Flux<FilePart> for multiple file upload
    @PostMapping(value = "/upload-flux", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<String> upload(@RequestPart("files") Flux<FilePart> filePartFlux) {
                /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        Flux<String> a = uploadService.getLines(filePartFlux);
        a.subscribe(s -> {
            System.out.println(s);
        });
        return a;
    }

    // use Flux<FilePart> for multiple file upload
    @PostMapping(value = "/upload-flux-2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload2(@RequestPart("files") Flux<FilePart> filePartFlux) {
                /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        Flux<String> a = uploadService.getLines(filePartFlux);
        return a.collectList();
    }


    // use single Mono<FilePart> for single file upload
    @PostMapping(value = "/upload-mono", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("file") Mono<FilePart> filePartMono) {

        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return uploadService.getLines(filePartMono).collectList();
    }

    // use single FilePart for single file upload
    @PostMapping(value = "/upload-filePart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("file") FilePart filePart) {

        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return uploadService.getLines(filePart).collectList();
    }

    // use Mono<MultiValueMap<String, Part>> for both single and multiple file upload under `files` param key
    @PostMapping(value = "/upload-multiValueMap", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> uploadFileMap(@RequestBody Mono<MultiValueMap<String, Part>> filePartMap) {
        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return uploadService.getLinesFromMap(filePartMap).collectList();
    }
}
