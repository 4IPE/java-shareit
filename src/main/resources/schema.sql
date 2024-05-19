drop table IF exists users,items,bookings,comments;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) ,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS items (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  available boolean ,
  owner_id BIGINT NOT NULL,
  CONSTRAINT pk_items PRIMARY KEY (id)
 );
 CREATE TABLE IF NOT EXISTS bookings (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   start_date TIMESTAMP NOT NULL,
   end_date TIMESTAMP NOT NULL,
   item_id BIGINT NOT NULL,
   booker_id BIGINT NOT NULL,
   status VARCHAR NOT NULL,
   CONSTRAINT pk_bookings PRIMARY KEY (id),
   CONSTRAINT fk_bookings_to_items FOREIGN KEY(item_id) REFERENCES items(id),
   CONSTRAINT fk_bookings_to_users FOREIGN KEY(booker_id) REFERENCES users(id)

  );
  CREATE TABLE IF NOT EXISTS comments (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
     text VARCHAR NOT NULL,
     item_id BIGINT NOT NULL,
     author_id BIGINT NOT NULL,
     CONSTRAINT fk_comments_to_items FOREIGN KEY(item_id) REFERENCES items(id),
     CONSTRAINT fk_comments_to_users FOREIGN KEY(author_id) REFERENCES users(id),
     CONSTRAINT pk_comment PRIMARY KEY (id)
    );