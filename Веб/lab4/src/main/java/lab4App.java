import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class lab4App{

	@PostMapping("/")
	public ResponseEntity<String> handlePostRequest(@RequestBody FormData data) {
		return ResponseEntity.ok("Запрос успешно обработан");
	}
}
