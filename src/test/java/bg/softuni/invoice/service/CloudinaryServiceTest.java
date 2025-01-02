package bg.softuni.invoice.service;

import bg.softuni.invoice.service.impl.CloudinaryServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceTest {

    private static final String IMAGE_URL = "https://mocked_url";
    private static final String MOCK_FILE_CONTENT = "Hello World";
    private static final Map<String, String> MOCK_RESPONSE_MAP = Map.of("url", IMAGE_URL);

    @InjectMocks
    private CloudinaryServiceImpl cloudinaryService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Test
    void testUploadImage_shouldReturnUrlCorrectly() throws IOException {
        MultipartFile multipartFile = createMockMultipartFile();
        File file = createTemporaryFile();

        try {
            multipartFile.transferTo(file);
            when(cloudinary.uploader()).thenReturn(uploader);
            when(uploader.upload(any(), any())).thenReturn(MOCK_RESPONSE_MAP);

            String imageUrl = cloudinaryService.uploadImage(multipartFile);

            assertThat(imageUrl).isEqualTo(IMAGE_URL);

            verify(cloudinary, times(1)).uploader();
            verify(uploader, times(1)).upload(any(), any());
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void testUploadImage_shouldThrowException_whenUploadFails() throws IOException {
        MultipartFile multipartFile = createMockMultipartFile();
        File file = createTemporaryFile();

        try {
            multipartFile.transferTo(file);
            when(cloudinary.uploader()).thenReturn(uploader);
            when(uploader.upload(any(), any())).thenThrow(IOException.class);

            assertThrows(IOException.class, () -> cloudinaryService.uploadImage(multipartFile));

            verify(cloudinary, times(1)).uploader();
            verify(uploader, times(1)).upload(any(), any());
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }

    private MultipartFile createMockMultipartFile() {
        return new MockMultipartFile("sourceFile.tmp", MOCK_FILE_CONTENT.getBytes());
    }

    private File createTemporaryFile() throws IOException {
        return Files.createTempFile(null, null).toFile();
    }
}
