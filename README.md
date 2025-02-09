# BankingWebapp


## Functionality:
Simple SpringBoot bank webapp honeypot designed to catch scammers by forcing them to validate their identity prior to withdrawal. The main focus of this project is primarily to target [Overpayment Scam](https://en.wikipedia.org/wiki/Overpayment_scam) perpetrators and those who are trying to empty accounts that do not belong to them.

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

## Disclaimer for "InterestingSnippets":

### Summary: 
Don't be an idiot and be responsible with usage. Pentesting without authorization is illegal.

### In depth: 
1. General Use: This software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose, and non-infringement. In no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.
2. Potential Misuse: The software is designed for legitimate purposes only. Any misuse, including but not limited to illegal, unethical, or unauthorized activities, is strictly discouraged and not the intention of the developers.
3. User Responsibility: Any person, entity, or organization choosing to use this software bears the full responsibility for its actions while using the software. It is the user's responsibility to ensure that their use of this software complies with local, state, national, and international laws and regulations.
4. No Liability: The creators, developers, and distributors of this software are not responsible for any harm or damage caused, directly or indirectly, by the misuse or use of this software.
5. Updates and Monitoring: The developers reserve the right to update, modify, or discontinue the software at any time. Users are advised to always use the most recent version of the software. However, even with updates, the developers cannot guarantee that the software is completely secure or free from vulnerabilities.
6. Third-Party Software/Links: This software may contain links to third-party sites or utilize third-party software/tools. The developers are not responsible for the content or privacy practices of those sites or software.
7. Unauthorized Access: Using "InterestingSnippets" to access, probe, or connect to systems, networks, or data without explicit permission from appropriate parties is strictly discouraged, unethical, and illegal. Unauthorized access to systems, networks, or data breaches various local, national, and international laws, and can result in severe legal consequences. Always obtain the necessary permissions before accessing any systems or data. The developers of "InterestingSnippets" disavow any actions taken by individuals or entities that use this software for unauthorized activities.

By downloading, installing, or using "InterestingSnippets" you acknowledge that you have read, understood, and agreed to abide by this disclaimer. If you do not agree to these terms, do not use the software.
