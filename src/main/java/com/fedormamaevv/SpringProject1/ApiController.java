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

    @GetMapping("topics/comments-by-user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable("username") String username)
    {
        List<Comment> commentsByUser = new ArrayList<>();
        for (Topic topic: topics)
        {
            for (Comment comment: topic.getComments())
            {
                if (comment.username.compareTo(username) == 0)
                    commentsByUser.add(comment);
            }
        }
        return commentsByUser;
    }

    @GetMapping("topics/{tIndex}/comments-by-user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username)
    {
        List<Comment> commentsByUser = new ArrayList<>();
        for (Comment comment: topics.get(topicIndex).getComments())
        {
            if (comment.username.compareTo(username) == 0)
                commentsByUser.add(comment);
        }
        return commentsByUser;
    }

    @DeleteMapping("topics/comments-by-user/{username}")
    public void deleteCommentsByUser(@PathVariable("username") String username)
    {
        for (int t = 0; t < topics.size(); t++)
        {
            Topic topic = topics.get(t);
            for (int c = 0; c < topic.getComments().size(); c++)
            {
                if (topic.getComments().get(c).username.compareTo(username) == 0)
                {
                    topic.deleteComment(c);
                }
            }
            topics.set(t, topic);
        }
    }

    @DeleteMapping("topics//{tIndex}/comments-by-user/{username}")
    public void deleteCommentsByUser(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username)
    {
        Topic topic = topics.get(topicIndex);
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                topic.deleteComment(c);
            }
        }
        topics.set(topicIndex, topic);
    }

    @PutMapping("topics/{tIndex}/comments-by-user/{username}")
    public void updateAllUserComments(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username, @RequestBody Comment comment)
    {
        Topic topic = topics.get(topicIndex);
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                topic.updateComment(c, comment);
            }
        }
        topics.set(topicIndex, topic);
    }

    @PutMapping("topics/{tIndex}/comments-by-user/{username}/{cIndex}")
    public void updateUserComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username, @PathVariable("cIndex") Integer commentIndex, @RequestBody Comment comment)
    {
        Topic topic = topics.get(topicIndex);
        int _commentIndex = commentIndex;
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                if (_commentIndex == 0) {
                    topic.updateComment(c, comment);
                    topics.set(topicIndex, topic);
                    return;
                }
                _commentIndex--;
            }
        }
        topics.set(topicIndex, topic);
    }

    @GetMapping("topics/{tIndex}/comments-by-user/{username}/count")
    public Integer getUserCommentCount(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username)
    {
        Topic topic = topics.get(topicIndex);
        int count = 0;
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0) count++;
        }
        return count;
    }

    @PutMapping("topics/{tIndex}/comments-by-user/{username}/by-content/{content}")
    public void updateUserCommentByContent(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username, @PathVariable("content") String content, @RequestBody Comment comment)
    {
        Topic topic = topics.get(topicIndex);
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                if (topic.getComments().get(c).text.indexOf(content) != -1)
                    topic.updateComment(c, comment);
            }
        }
        topics.set(topicIndex, topic);
    }
}