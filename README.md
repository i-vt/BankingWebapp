# BankingWebapp
Simple SpringBoot bank webapp honeypot

## Functionality:

### ServerSide (Webapp)

Index:
![Screenshot from 2024-11-05 17-43-54](https://github.com/user-attachments/assets/7babb40a-6f56-4266-87fb-08e05a634d25)

Logged in user's home page:
![Screenshot from 2024-11-05 17-45-42](https://github.com/user-attachments/assets/1f6a6b0d-0ad4-4369-900a-9dd140f89373)


Resource center:
![Screenshot from 2024-11-05 17-46-06](https://github.com/user-attachments/assets/191434f9-f2ce-4f24-bdb9-1493f9a1296e)


### Verify Identity (API)

Catch the thief's hand when trying to withdraw funds
![Screenshot from 2024-11-05 17-47-47](https://github.com/user-attachments/assets/e449d944-52fb-4f79-bbdd-83f9e8c12dd9)

```
curl -X POST 'http://localhost:8082/api/register-uuid?uuid=123e4567-e89b-12d3-a456-426614174000'
```

```
curl -X POST 'http://localhost:8082/api/populate-details/123e4567-e89b-12d3-a456-426614174000' \
     -F 'fullName=John Doe' \
     -F 'address=123 Maple Street' \
     -F 'faceImage=@/path/to/face.jpg' \
     -F 'idCardImage=@/path/to/id_card.jpg'
```

### BankAPI 
Enables basic transfer between accounts to emulate some resemblance of functionality
![Screenshot from 2024-11-05 17-45-42](https://github.com/user-attachments/assets/d0f5af03-d51b-4b52-8b1d-55261866e34c)
