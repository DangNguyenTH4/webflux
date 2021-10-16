package com.example.webflux.service;

import com.example.webflux.util.MultipartFileUploadUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UploadService {
  public Flux<String> getLines(Mono<FilePart> filePartMono) {

    return filePartMono.flatMapMany(this::getLines);
  }

  public Flux<String> getLines(Flux<FilePart> filePartFlux) {

    return filePartFlux.flatMap(this::getLines);
  }

  public Flux<String> getLines(FilePart filePart) {
      return filePart.content()
              .map(dataBuffer -> {
                  byte[] bytes = new byte[dataBuffer.readableByteCount()];
                  dataBuffer.read(bytes);
                  DataBufferUtils.release(dataBuffer);

                  return new String(bytes, StandardCharsets.UTF_8);
              })
              .map(this::processAndGetLinesAsList)
              .flatMapIterable(Function.identity());
  }
    // this is for both single and multiple file upload under `files` param key
    public Flux<String> getLinesFromMap(Mono<MultiValueMap<String, Part>> filePartMap) {
        return filePartMap.flatMapIterable(map ->
                map.keySet().stream()
                        .filter(key -> key.equals("files"))
                        .flatMap(key -> map.get(key).stream().filter(part -> part instanceof FilePart))
                        .collect(Collectors.toList()))
                .flatMap(part -> getLines((FilePart) part));
    }

  private List<String> processAndGetLinesAsList(String string) {
    Supplier<Stream<String>> streamSupplier = string::lines;
    var isFileOk = true;
//        streamSupplier.get().allMatch(line -> line.matches(MultipartFileUploadUtils.REGEX_RULES));
    return isFileOk
        ? streamSupplier.get().filter(s -> !s.isBlank()).collect(Collectors.toList())
        : new ArrayList<>();
  }
}
