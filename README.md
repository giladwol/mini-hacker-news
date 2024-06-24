# Mini Hacker News

This project is a mini version of the Hacker News website, built with Angular for the frontend, Java for the backend, and MongoDB for the database. The project is containerized using Docker.

## Exploring New Technologies

Before starting this project, I hadn't had the chance to work with these technologies. However, I saw it as an opportunity to expand my skillset and gain experience with popular tools in the industry.

By investing time in learning and experimenting with Spring, Docker, and MongoDB, I have been able to enhance my career prospects and stay up-to-date with the latest industry trends. It has been a rewarding journey of exploring new technologies and discovering their potential.

## Running the Project

1. Ensure Docker is installed on your system.
2. Navigate to the project directory.
3. Run the following command:

```sh
docker-compose up
```

This command will start all services defined in the `docker-compose.yml` file, including the Angular frontend, Java backend, and MongoDB database.

The client-side application will be accessible at [http://localhost:4400](http://localhost:4400). The server-side application will be running at `http://localhost:8080`. The database can be accessed at `http://localhost:27017`.

## Post Score Calculation and Caching

The score of a post in Mini Hacker News is determined by a combination of the post's votes and its creation time. This score is used to order the posts on the "Top Posts" page, which is requested thousands of times per second.

### Score Calculation

Each post's score is calculated by summing the value of each vote divided by the age of the vote in seconds. This calculation is performed in the `updateScore` method of the `Post` class:

```java
this.score = this.getVotes().stream().mapToDouble(vote -> {
  return vote.getScore(this);
}).sum();
```

### Chacing

Given the high frequency of requests to the "Top Posts" page and the potentially large number of posts in the database, efficiency is critical. To optimize performance, the calculated score of each post is stored in a cache. If the score is already cached and the 'force' flag is false, the cached score is used instead of recalculating the score:

```java
if (!force && scoreCache.containsKey(this.getId())) {
  this.score = scoreCache.get(this.getId());
} else {
  // score calculation...
  scoreCache.put(this.getId(), this.score);
}
```

This caching mechanism significantly reduces the computational load of score calculation, especially when the "Top Posts" page is being requested frequently.

# RESTful API Endpoints

The application provides several RESTful API endpoints for interacting with the Mini Hacker News system. Here's a brief overview of each endpoint:

## Post Management

#### `GET /api/posts`

- **Purpose:** Fetch all posts from the database.
- **Expected Input Parameters:** None.
- **Response Format:** A JSON array of posts.

#### `POST /api/posts`

- **Purpose:** Create a new post in the database.
- **Expected Input Parameters:** A JSON object containing `text` property.
- **Response Format:** The created post as a JSON object.

#### `PUT /api/posts/{id}`

- **Purpose:** Update a specific post in the database.
- **Expected Input Parameters:** The ID of the post as a path parameter, and a JSON object containing `text` property.
- **Response Format:** The updated post as a JSON object.

#### `POST /api/posts/{id}/delete`

- **Purpose:** Delete a specific post in the database.
- **Expected Input Parameters:** The ID of the post as a path parameter.
- **Response Format:** No content (HTTP 204 status code) on successful deletion.

## Post Voting

#### `POST /api/posts/{id}/upvote`

- **Purpose:** Upvote a specific post in the database.
- **Expected Input Parameters:** The ID of the post as a path parameter.
- **Response Format:** The upvoted post as a JSON object.

#### `POST /api/posts/{id}/downvote`

- **Purpose:** Downvote a specific post in the database.
- **Expected Input Parameters:** The ID of the post as a path parameter.
- **Response Format:** The downvoted post as a JSON object.

## Post Retrieval

#### `GET /api/posts/top`

- **Purpose:** Fetch the top posts from the database.
- **Expected Input Parameters:** An optional query parameter `limit` that defaults to 10 if not provided.
- **Response Format:** A JSON array of the top posts.

#### `GET /api/posts/new`

- **Purpose:** Fetch the newest posts from the database.
- **Expected Input Parameters:** An optional query parameter `limit` that defaults to 10 if not provided.
- **Response Format:** A JSON array of the newest posts.
