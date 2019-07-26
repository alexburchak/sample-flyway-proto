package sample.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import sample.flyway.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class SampleFlywayApplicationTests extends AbstractTestNGSpringContextTests {
	@Autowired
	private Flyway flyway;
	@Autowired
	private JdbcTemplate template;

	@Test
	public void testDefaultSettings() {
		try {
			flyway.migrate();
		} finally {
			List<Person> list = template.query("select * from Person ORDER BY id", new BeanPropertyRowMapper(Person.class));
			list.forEach(System.out::println);

			assertThat(list.size()).isEqualTo(2);
		}
	}
}
