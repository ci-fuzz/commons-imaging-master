
package fuzz_targets;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;

import java.awt.Dimension;
import java.awt.color.ICC_Profile;

import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;

public class AllImageParser {
	public static void fuzzerTestOneInput(FuzzedDataProvider data) {

		final BufferedImage image;
		try{
			byte[] imagebytes = data.consumeBytes(data.consumeInt(1,Integer.MAX_VALUE));
			image = Imaging.getBufferedImage(imagebytes);
			final ImageMetadata metadata = Imaging.getMetadata(imagebytes);
			final ICC_Profile iccProfile = Imaging.getICCProfile(imagebytes);
			final ImageInfo imageInfo = Imaging.getImageInfo(imagebytes);

		} catch(ImageReadException | IOException e){
			return;
		}

		final ImageFormat format = ImageFormats.values()[data.consumeInt(0,14)];
		final Map<String, Object> params = new HashMap<>();

		try{
        	Imaging.writeImageToBytes(image, format, params);
		} catch(ImageWriteException | IOException e){
			return;
		}
	}
}
