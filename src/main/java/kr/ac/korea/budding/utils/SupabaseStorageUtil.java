package kr.ac.korea.budding.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SupabaseStorageUtil {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    private final WebClient webClient = WebClient.builder().build();


    // supabase bucket에 업로드하는 메서드
    public String uploadFileToBucket(MultipartFile file, String path) {
        try {
            byte[] bytes = file.getBytes();

            String url = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucketName, path);

            webClient.put()
                    .uri(url)
                    .header("apikey", supabaseKey)
                    .header("Authorization", "Bearer " + supabaseKey)
                    .contentType(MediaType.parseMediaType(file.getContentType() != null ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE))
                    .bodyValue(bytes)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new RuntimeException("Supabase upload error: " + errorBody)))
                    )
                    .toBodilessEntity()
                    .block(); // 동기 방식으로 사용 (간단히 예시라서)

            return path;

        } catch (IOException e) {
            throw new RuntimeException("failed to read file", e);
        }
    }

}
