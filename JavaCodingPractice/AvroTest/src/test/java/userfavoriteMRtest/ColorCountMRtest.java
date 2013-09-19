package userfavoriteMRtest;

import java.io.File;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import userfavoriteMapReduce.ColorCount;
import static org.junit.Assert.assertTrue;

public class ColorCountMRtest {
	public static boolean deleteDirectory(final File dir) {

		if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i = 0; i < children.length; i++) {
	                boolean success = deleteDirectory(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	@Test
    public void testSingleInput() throws Exception {


        Configuration configuration = new Configuration();

        configuration.set("fs.default.name", "file:///");
        configuration.set("mapred.job.tracker", "local");

        URL inputDirectoryURL = ColorCountMRtest.class
        .getResource("/userfavoriteMRfiles/input");
        configuration.set("input", inputDirectoryURL.toString());
        String outputDirectoryPathString = "target/output";
        ColorCountMRtest.deleteDirectory(new File(outputDirectoryPathString));
        configuration.set("output", outputDirectoryPathString);

        ColorCount driver = new ColorCount();
        driver.setConf(configuration);
        int exitCode = driver.run(null);
        assertTrue("The exist code should be 0!", 0 == exitCode);
    }

}
