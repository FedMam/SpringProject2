package com.fedormamaevv.SpringProject1;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApiController {

    private List<Topic> topics = new ArrayList<>();

    @PostMapping("topics")
    public void addTopic(@RequestBody Topic topic) { topics.add(topic); }

    @DeleteMapping("topics/{index}")
    public void removeTopic(@PathVariable("index") Integer index)
    {
        if (index >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        topics.remove((int)index);
    }

    private List<Topic> sortTopics(List<Topic> topics, String sort_by, String sort_order)
    {
        Topic[] topicArray = topics.toArray(new Topic[0]);
        if (sort_by.compareTo("created") == 0 || sort_by.compareTo("updated") == 0) {
            Arrays.sort(topicArray, new Comparator<Topic>() {
                @Override
                public int compare(Topic topic, Topic t1) {
                    
                    int rev = 1;
                    if (sort_order.compareTo("descending") == 0) rev = -1;
                    if (sort_by.compareTo("created") == 0) {
                        if (topic.created == null) return -rev;
                        if (t1.created == null) return rev;
                        return rev * topic.created.compareTo(t1.created);
                    }
                    else {
                        if (topic.updated == null) return -rev;
                        if (t1.updated == null) return rev;
                        return rev * topic.updated.compareTo(t1.updated);
                    }
                }
            });
        }
        List<Topic> topicList = new ArrayList<>();
        for (Topic topic: topicArray)
            topicList.add(topic);
        return topicList;
    }

    private List<Comment> sortComments(List<Comment> comments, String sort_by, String sort_order)
    {
        Comment[] commentArray = comments.toArray(new Comment[0]);
        if (sort_by.compareTo("created") == 0 || sort_by.compareTo("updated") == 0) {
            Arrays.sort(commentArray, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment, Comment t1) {
                    int rev = 1;
                    if (sort_order.compareTo("descending") == 0) rev = -1;
                    if (sort_by.compareTo("created") == 0) {
                        if (comment.created == null) return -rev;
                        if (t1.created == null) return rev;
                        return rev * comment.created.compareTo(t1.created);
                    }
                    else {
                        if (comment.updated == null) return -rev;
                        if (t1.updated == null) return rev;
                        return rev * comment.updated.compareTo(t1.updated);
                    }
                }
            });
        }
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment: commentArray)
            commentList.add(comment);
        return commentList;
    }

    @GetMapping("topics")
    public List<Topic> getAllTopics(@RequestParam(name="sort-by", defaultValue = "dont-sort", required = false) String sort_by,
                                    @RequestParam(name="sort-order", defaultValue = "ascending", required = false) String sort_order)
    {
        return sortTopics(topics, sort_by, sort_order);
    }

    @GetMapping("topics/{index}")
    public Topic getTopic(@PathVariable("index") Integer index)
    {
        if (index >= topics.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        return topics.get(index);
    }

    @PutMapping("topics/{index}")
    public void updateTopic(@PathVariable("index") Integer index, @RequestBody Topic newTopic)
    {
        if (index >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        topics.set(index, newTopic);
    }

    @GetMapping("topics/count")
    public Integer count() { return topics.size(); }

    @DeleteMapping("topics/clear")
    public void clear() { topics.clear(); }

    // -----

    @PostMapping("topics/{index}/comments")
    public void addComment(@PathVariable("index") Integer topicIndex, @RequestBody Comment comment)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        Topic topic = topics.get(topicIndex);
        topic.addComment(comment);
        // topics.set(topicIndex, topic);
    }

    @GetMapping("topics/{index}/comments")
    public List<Comment> getAllComments(@PathVariable("index") Integer topicIndex,
                                        @RequestParam(name="sort-by", defaultValue = "dont-sort", required = false) String sort_by,
                                        @RequestParam(name="sort-order", defaultValue = "ascending", required = false) String sort_order)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        return sortComments(topics.get(topicIndex).getComments(), sort_by, sort_order);
    }

    @GetMapping("topics/{tIndex}/comments/{cIndex}")
    public Comment getComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        if (commentIndex >= topics.get(topicIndex).comments.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        return topics.get(topicIndex).getComment(commentIndex);
    }

    @PutMapping("topics/{tIndex}/comments/{cIndex}")
    public void updateComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex, @RequestBody Comment comment)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        Topic topic = topics.get(topicIndex);
        topic.updateComment(commentIndex, comment);
        // topics.set(topicIndex, topic);
    }

    @DeleteMapping("topics/{tIndex}/comments/{cIndex}")
    public void deleteComment(@PathVariable("tIndex") Integer topicIndex, @PathVariable("cIndex") Integer commentIndex)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        Topic topic = topics.get(topicIndex);
        topic.deleteComment(commentIndex);
        // topics.set(topicIndex, topic);
    }

    // -----

    @GetMapping("topics/comments-by-user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable("username") String username,
                                           @RequestParam(name="sort-by", defaultValue = "dont-sort", required = false) String sort_by,
                                           @RequestParam(name="sort-order", defaultValue = "ascending", required = false) String sort_order)
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
        return sortComments(commentsByUser, sort_by, sort_order);
    }

    @GetMapping("topics/{tIndex}/comments-by-user/{username}")
    public List<Comment> getCommentsByUser(@PathVariable("tIndex") Integer topicIndex,
                                           @PathVariable("username") String username,
                                           @RequestParam(name="sort-by", defaultValue = "dont-sort", required = false) String sort_by,
                                           @RequestParam(name="sort-order", defaultValue = "ascending", required = false) String sort_order)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        List<Comment> commentsByUser = new ArrayList<>();
        for (Comment comment: topics.get(topicIndex).getComments())
        {
            if (comment.username.compareTo(username) == 0)
                commentsByUser.add(comment);
        }
        return sortComments(commentsByUser, sort_by, sort_order);
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

    @DeleteMapping("topics/{tIndex}/comments-by-user/{username}")
    public void deleteCommentByUser(@PathVariable("tIndex") Integer topicIndex,
                                    @PathVariable("username") String username)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        Topic topic = topics.get(topicIndex);
        for (int c = 0; c < topic.getComments().size(); )
        {
            boolean flag = true;
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                topic.deleteComment(c);
                flag = false;
            }
            if (flag) c++;
        }
        topics.set(topicIndex, topic);
    }

    @PutMapping("topics/{tIndex}/comments-by-user/{username}")
    public void updateUserComment(@PathVariable("tIndex") Integer topicIndex,
                                      @PathVariable("username") String username,
                                      @RequestBody Comment comment,
                                      @RequestParam(required = false, defaultValue = "0") String index,
                                      @RequestParam(required = false, defaultValue = "all") String query,
                                      @RequestParam(required = false, defaultValue = "") String content)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return;
        }
        int commentIndex = -1;
        try {
            commentIndex = Integer.parseInt(index);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Topic topic = topics.get(topicIndex);
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0)
            {
                if ((query.compareTo("all") == 0) ||
                        (query.compareTo("by-index") == 0 && commentIndex == 0) ||
                        (query.compareTo("by-content") == 0) && topic.getComments().get(c).text.contains(content)) {
                    topic.updateComment(c, comment);
                }
                commentIndex--;
            }
        }
        topics.set(topicIndex, topic);
    }

    @GetMapping("topics/{tIndex}/comments-by-user/{username}/count")
    public Integer getUserCommentCount(@PathVariable("tIndex") Integer topicIndex, @PathVariable("username") String username)
    {
        if (topicIndex >= topics.size())
        {
            System.out.println("Index out of range");
            return null;
        }
        Topic topic = topics.get(topicIndex);
        int count = 0;
        for (int c = 0; c < topic.getComments().size(); c++)
        {
            if (topic.getComments().get(c).username.compareTo(username) == 0) count++;
        }
        return count;
    }
}