package karol.wlazlo.ds.update.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import karol.wlazlo.commons.exceptions.CarRentalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfurufcqe",
                "api_key", "124119393862875",
                "api_secret", "8kL07GhSW8a_qX4tnOcDD4GU6eE"
        ));

        SingletonManager manager = new SingletonManager();
        manager.setCloudinary(cloudinary);
    }

    public String upload(InputStream file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.readAllBytes(), ObjectUtils.emptyMap());

            return uploadResult.get("public_id").toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("cloudinary.service.upload.exception " + e.getMessage());
            throw new CarRentalException("msg.err.cloudinary.service.upload");
        }
    }

    public void delete(String id) {
        try {
            cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("cloudinary.service.upload.exception " + e.getMessage());
            throw new CarRentalException("msg.err.cloudinary.service.delete");
        }
    }
}
