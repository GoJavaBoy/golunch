package co.uk.golunch.web.vote;

import co.uk.golunch.service.VotesService;
import co.uk.golunch.web.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/vote";

    private final VotesService votesService;

    public VoteController(VotesService votesService) {
        this.votesService = votesService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable("id") int resId) {
        votesService.save(SecurityUtil.get().getUser(), resId);
    }

}
