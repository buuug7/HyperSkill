import assistant.PersonalAssistant;
import org.hyperskill.hstest.v4.stage.MainMethodTest;
import org.hyperskill.hstest.v4.testcase.CheckResult;
import org.hyperskill.hstest.v4.testcase.TestCase;

import java.util.List;


public class PersonalAssistantTest extends MainMethodTest<String> {

    public PersonalAssistantTest() throws Exception {
        super(PersonalAssistant.class);
    }

    private static final String REGEX_TEMPLATE =
        "(.+\\n){3}(.+)%s(.+\\n){3}(.+)%d(.+\\n){2}(\\d+!\\n)+(.+\\n){2}" +
            "(\\d+\\..+)+(.+\\n)*Congratulations, have a nice day!\\n?";

    @Override
    public List<TestCase<String>> generateTestCases() {
        return List.of(
            new TestCase<String>()
                .setInput("Marry\n1 0 5\n10\n4 2")
                .setAttach("Marry")
        );
    }

    @Override
    public CheckResult check(String reply, String clue) {
        String regex = String.format(REGEX_TEMPLATE, clue, 40);
        return new CheckResult(reply.matches(regex));
    }

}
