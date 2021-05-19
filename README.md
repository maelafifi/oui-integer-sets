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
    set_unique_id varchar(64) NOT NULL UNIQUE,
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
```
git clone https://github.com/maelafifi/oui-integer-sets.git
cd oui-integer-sets 
git checkout oui-feature
```
#### You should be able to test it out, now. Run the following to start the server -- and I am hoping you have java1.8
cd back down one level and run `idea oui-integer-sets`

intellij should open up; once in intellij, click on run -> edit configurations
A pop-up should come up. In the top left corner of the pop up, click the + button and select maven project
A new dialogue will show up; 
#### parameters tab
1. update the working directory and make this the root directory e.g. `/<ABSOLUTE PATH>/oui-integer-sets`
2. update the command line to read, `spring-boot:run -Dspring.profiles.label=local -Dspring.profiles.active=local -Dserver.port=8080` (you can replace port as needed)
#### runner tab
1. Update the jre to be Java1.8

Save, then click the play button on intellij. The application should start up. 

## Querying and mutating
Navigate to `http://localhost:8080/graphiql` and you will have a ui for querying and mutating the service.
You will also be able to take a look at the schema... that you designed.... so not sure how useful that is. 

## Queries to test 
#### creating a new set -- try this, twice. First time will succeed, second will fail due to duplicate values
```
mutation {
  createSet(input:{ members:[1,2,3,4] }) {
    members
  }
}
```

##### Change the array to any list of integers in the above mutation to test persisting different data, good or bad.

#### Once you've created a few sets, run the following query to retrieve all sets and their intersecting sets
``` 
query {
	sets {
    members
  	intersectingSets {
    	members
  	}
    }
}
```
