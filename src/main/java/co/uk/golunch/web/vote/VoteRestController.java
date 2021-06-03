package co.uk.golunch.web.vote;

import co.uk.golunch.model.Vote;
import co.uk.golunch.service.VotesService;
import co.uk.golunch.web.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/vote";

    private final VotesService votesService;

    public VoteRestController(VotesService votesService) {
        this.votesService = votesService;
    }

    @PostMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void vote(@PathVariable("restaurantId") int restaurantId) {
        votesService.save(SecurityUtil.get().getUser(), restaurantId);
    }

    @GetMapping("/today")
    public Vote getTodayVote(){
        return votesService.getTodayVote(SecurityUtil.get().getUser());
    }

    @GetMapping("/all")
    public List<Vote> getAllVotes(){
        return votesService.getAllVotes(SecurityUtil.get().getUser());
    }
}
