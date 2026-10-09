package in.chandan.WatchTrack;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  WatchTrackApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                        .directory(".")
                                .ignoreIfMissing().load();

        System.setProperty("TMDB_API_TOKEN",dotenv.get("TMDB_API_TOKEN"));

		SpringApplication.run(WatchTrackApplication.class, args);
	}

}
