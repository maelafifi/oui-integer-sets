# oui-integer-sets

# Usage
#### Assuming you have docker on your machine, run the following to start a new docker postgres container:
```
docker run --name oui-integer -e POSTGRES_PASSWORD=root -d -p 5432:5432 postgres
```
#### Connect to the docker container and connect to the psql instance
First, you should find the container_id for the newly created container by running:
```
docker ps
```

Next, you should run the following two commands, first connects to the container, second connects to the rds:
```
docker exec -it <INSERT CONTAINER ID HERE> bash
psql -U postgres
```

#### Create the database and database tables
```
create database sets;

\c sets;

create table set (
    set_id SERIAL PRIMARY KEY,
    set_members integer[] NOT NULL UNIQUE
);

create table set_member (
    set_member_id SERIAL PRIMARY KEY,
    set_id int NOT NULL,
    set_member_entry_num int NOT NULL,
    set_member_value int NOT NULL,
    CONSTRAINT fk_set FOREIGN KEY(set_id) REFERENCES set(set_id) ON DELETE CASCADE
);
```

#### Now that you have that out of the way, it's probably a good idea to clone this repository, then cd into it. 
#### You'll want to use the oui-feature branch
#### Once you've cloned the repository, cd into the root of the project  