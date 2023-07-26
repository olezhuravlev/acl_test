## PIM Spring Security ACL DEMO

### 1. How to start

````yaml
$ docker-compose up -d
[+] Running 4/4
✔ Network rights_ss_demo_default  Created    0.1s
✔ Container docker-hoster         Started    0.7s
✔ Container postgres              Healthy   31.4s
✔ Container pgadmin               Started
````

As a result, we will have three docker-containers up:

- docker-hoster;
- postgres;
- pg-admin.

### 2. Security roles

The next roles:

- ROLE_ADMIN ("admin/admin"): any operations allowed;
- ROLE_USER ("user/user"): all operations allowed besides deletion;
- ROLE_READER ("reader/reader"): reading only.

### 3. Access

- GET `http://localhost:8080/login` to authenticate;
- GET `http://localhost:8080/logout` to logout;
- POST `http://localhost:8080/items` to get list of all items;
- POST `http://localhost:8080/items/{id}` to get item of specified ID;

P.S.
You have to use Swagger GUI to access these POST-requests. Swagger is reachable at 
`http://localhost:8080/swagger-ui/index.html`.

---
