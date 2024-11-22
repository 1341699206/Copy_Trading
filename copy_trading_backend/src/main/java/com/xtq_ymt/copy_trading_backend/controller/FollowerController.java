package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.FollowerTrader;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
@Tag(name = "Follower Management", description = "APIs for managing followers and traders")
public class FollowerController {

    private final FollowerService followerService;

    @Autowired
    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    @PostMapping("/follow")
    @Operation(summary = "Follow a trader", description = "Allows a follower to follow a specific trader.")
    public ResponseEntity<FollowerTrader> followTrader(@RequestParam Long followerAccountId,
                                                        @RequestParam Long traderAccountId) {
        FollowerTrader followerTrader = followerService.followTrader(followerAccountId, traderAccountId);
        return new ResponseEntity<>(followerTrader, HttpStatus.CREATED);
    }

    @DeleteMapping("/unfollow")
    @Operation(summary = "Unfollow a trader", description = "Allows a follower to unfollow a specific trader.")
    public ResponseEntity<String> unfollowTrader(@RequestParam Long followerAccountId,
                                                  @RequestParam Long traderAccountId) {
        followerService.unfollowTrader(followerAccountId, traderAccountId);
        return new ResponseEntity<>("Unfollowed successfully.", HttpStatus.OK);
    }

    @GetMapping("/{followerAccountId}/traders")
    @Operation(summary = "Get followed traders", description = "Fetches the list of traders followed by a follower.")
    public ResponseEntity<List<FollowerTrader>> getFollowedTraders(@PathVariable Long followerAccountId) {
        List<FollowerTrader> followedTraders = followerService.getFollowedTraders(followerAccountId);
        return new ResponseEntity<>(followedTraders, HttpStatus.OK);
    }
}
