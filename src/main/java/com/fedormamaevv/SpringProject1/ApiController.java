package com.fedormamaevv.SpringProject1;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiController {

    private List<Topic> topics = new ArrayList<>();

    @PostMapping("topics")
    public void addTopic(@RequestBody Topic topic) { topics.add(topic); }

    @DeleteMapping("topics/{index}")
    public void removeTopic(@PathVariable("index") Integer index) { topics.remove((int)index); }

    @GetMapping("topics")
    public List<Topic> getAllTopics() { return topics; }

    @GetMapping("topics/{index}")
    public Topic getTopic(@PathVariable("index") Integer index) { return topics.get(index); }

    @PutMapping("topics/{index}")
    public void updateTopic(@PathVariable("index") Integer index, @RequestBody Topic newTopic) { topics.set(index, newTopic); }

    @GetMapping("topics/count")
    public Integer count() { return topics.size(); }

    @DeleteMapping("topics/clear")
    public void clear() { topics.clear(); }

    // -----

    @PostMapping("topics/{index}/comments")
    public void addComment(@PathVariable("index") Integer topicIndex, @RequestBody Comment comment)
    {
        Topic topic = topics.get(topicIndex);
        topic.addComment(comment);
        topics.set(topicIndex, topic);
    }

    @GetMapping("topics/{index}/comments")
    public List<Comment> getAllComments(@PathVariable("index") Integer topicIndex)
    {
        return topics.get(topicIndex).getComments();
    }

    @GetMapping("topics/{tIndex}/comments/{cIndex}")
    public Comment getComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex)
    {
        return topics.get(topicIndex).getComment(commentIndex);
    }

    @PutMapping("topics/{tIndex}/comments/{cIndex}")
    public void updateComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex, @RequestBody Comment comment)
    {
        Topic topic = topics.get(topicIndex);
        topic.updateComment(commentIndex, comment);
        topics.set(topicIndex, topic);
    }

    @DeleteMapping("topics/{tIndex}/comments/{cIndex}")
    public void deleteComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex)
    {
        Topic topic = topics.get(topicIndex);
        topic.deleteComment(commentIndex);
        topics.set(topicIndex, topic);
    }

    // -----

}