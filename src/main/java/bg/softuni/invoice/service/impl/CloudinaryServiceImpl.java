package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE_PREFIX = "cloudinary_upload_";
    private static final String POSIX_FILE_PERMISSION = "rw-------";
    private static final String TEMP_FOLDER = "/temp";

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File tempFile = createTemporaryFile();

        try {
            multipartFile.transferTo(tempFile);

            return this.cloudinary
                    .uploader()
                    .upload(tempFile, ObjectUtils.emptyMap())
                    .get("url").toString();
        } finally {
            try {
                Files.deleteIfExists(tempFile.toPath());
            } catch (Exception e) {
                log.error("Warning: Could not delete temp file: {}", tempFile.getAbsolutePath(), e);
            }
        }
    }

    private File createTemporaryFile() throws IOException {
        if (SystemUtils.IS_OS_UNIX) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(
                    PosixFilePermissions.fromString(POSIX_FILE_PERMISSION));
            Path tempFile = Files.createTempFile(TEMP_FILE_PREFIX, null, attr);
            return tempFile.toFile();
        } else {
            String currentDir = Paths.get("").toAbsolutePath().toString();
            Path tempDir = Paths.get(currentDir + TEMP_FOLDER);

            if (Files.notExists(tempDir)) {
                Files.createDirectory(tempDir);
            }

            Path tempFile = Files.createTempFile(tempDir, TEMP_FILE_PREFIX, null);
            return tempFile.toFile();
        }
    }
}
