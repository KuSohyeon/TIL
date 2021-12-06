## ROLE

### ROLE ASSIGNMENT
- List role assignments
- 할당된 role 확인 시 사용
```java
openstack role assignment list
        [--role <role>]
        [--role-domain <role-domain>]
        [--user <user>]
        [--user-domain <user-domain>]
        [--group <group>]
        [--group-domain <group-domain>]
        [--domain <domain>]
        [--project <project>]
        [--project-domain <project-domain>]
        [--effective]
        [--inherited]
        [--names]
        
(openstack) role assignment list --user {username}
Password:
+----------------------+----------+-------+-------------------+---------+--------+-----------+
| Role                 | User   - | Group | Project           | Domain  | System | Inherited |
+----------------------+----------+-------+-------------------+---------+--------+-----------+
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
| ******************** | username |       | ***************** |         |        | False     |
+----------------------+----------+-------+-------------------+---------+--------+-----------+
```
### ROLE ADD
- Add role assignment to a user or group in a project or domain
- user/group에 role을 할당 시 사용
```java
openstack role add
        --domain <domain> | --project <project> [--project-domain <project-domain>]
        --user <user> [--user-domain <user-domain>] | --group <group> [--group-domain <group-domain>]
        --role-domain <role-domain>
        --inherited
        <role>

(openstack) role add --project {project} --user {username} {role}
Password:
// 별도의 출력 없음
```

### ROLE REMOVE
- Remove role assignment from domain/project : user/group
- 사용자/그룹에 할당된 role 삭제 시 사용
```java
openstack role remove
    --domain <domain> | --project <project> [--project-domain <project-domain>]
    --user <user> [--user-domain <user-domain>] | --group <group> [--group-domain <group-domain>]
    --role-domain <role-domain>
    --inherited
    <role>

(openstack) role remove --domain default --user {username} {role}
// 별도의 출력 없음
```
