1. Get Account Balance

bash

curl -X GET "http://localhost:8080/api/bank/balance/{accountNumber}" -H "Content-Type: application/json"

Replace {accountNumber} with the actual account number.
2. Transfer Funds Between Accounts

bash

curl -X POST "http://localhost:8080/api/bank/transfer" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "fromAccount={fromAccount}" \
--data-urlencode "toAccount={toAccount}" \
--data-urlencode "amount=100.00"

Replace {fromAccount} and {toAccount} with the account numbers and 100.00 with the amount to transfer.
3. Add Funds to an Account

bash

curl -X POST "http://localhost:8080/api/bank/add-funds" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "accountNumber={accountNumber}" \
--data-urlencode "amount=50.00"

Replace {accountNumber} with the account number and 50.00 with the amount to add.
4. Generate Accounts for a User

bash

curl -X POST "http://localhost:8080/api/bank/generate-accounts" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "userId={userId}" \
--data-urlencode "numAccounts=3"

Replace {userId} with the user UUID and 3 with the number of accounts to generate.
5. Add Account to a User

bash

curl -X POST "http://localhost:8080/api/bank/add-account" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "userId={userId}" \
--data-urlencode "accountNumber={newAccountNumber}" \
--data-urlencode "initialBalance=1000.00"

Replace {userId} with the user UUID, {newAccountNumber} with the new account number, and 1000.00 with the initial balance.
6. Delete an Account

bash

curl -X DELETE "http://localhost:8080/api/bank/delete-account/{accountId}" -H "Content-Type: application/json"

Replace {accountId} with the UUID of the account to delete.
7. Create a New User with a Default Account

bash

curl -X POST "http://localhost:8080/api/bank/create-user" \
-H "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "userName=JohnDoe"

Replace JohnDoe with the desired username.


Get transaction summary:
curl -X GET "http://localhost:8080/api/bank/transaction-history/ACC35145"
[{"id":"510cf3a9-4620-47c6-8e5b-dc46b13c3b39","type":"TRANSFER","amount":-500.00,"timestamp":"2024-10-10T05:41:20.248664","accountNumber":"ACC35145"},{"id":"fa7913c7-1c46-4bef-8ec2-daca0441cb03","type":"ACCOUNT_CREATION","amount":8972.80,"timestamp":"2024-10-10T05:41:00.235718","accountNumber":"ACC35145"}]

curl -X GET "http://localhost:8080/api/bank/accounts/cb847550-714d-4a36-8840-d9ce2b1015ef"

[{"id":"6154b339-6835-4e13-af3f-d1b4e421dda4","accountNumber":"ACC324313","balance":7007.10,"user":{"id":"6f6abc4d-a3a1-40a0-a98d-8d34d43fc27b","name":"User3"},"transactions":[{"id":"d50bb37c-d103-4324-b4d1-3ec50119dbf6","type":"ACCOUNT_CREATION","amount":7007.10,"timestamp":"2024-10-18T22:18:42.082185"}]},{"id":"a460f815-2bce-4cd1-b6b6-f3948c8946b8","accountNumber":"ACC357824","balance":603.01,"user":{"id":"6f6abc4d-a3a1-40a0-a98d-8d34d43fc27b","name":"User3"},"transactions":[{"id":"eb6743a6-72d5-450e-8c92-cc4e8f6c03a5","type":"ACCOUNT_CREATION","amount":603.01,"timestamp":"2024-10-18T22:18:42.084647"}]},{"id":"bed4592f-2ea8-4b94-bb55-a01f4e7781a3","accountNumber":"ACC316449","balance":7463.46,"user":{"id":"6f6abc4d-a3a1-40a0-a98d-8d34d43fc27b","name":"User3"},"transactions":[{"id":"4bbfa2c3-2cd6-4c55-b694-0f78502f8a88","type":"ACCOUNT_CREATION","amount":7463.46,"timestamp":"2024-10-18T22:18:42.081389"}]},{"id":"baf30217-3539-4bc7-ba33-f75017120344","accountNumber":"ACC33283","balance":3224.38,"user":{"id":"6f6abc4d-a3a1-40a0-a98d-8d34d43fc27b","name":"User3"},"transactions":[{"id":"f0aab488-0b77-4ba0-aeed-95c9889adec1","type":"ACCOUNT_CREATION","amount":3224.38,"timestamp":"2024-10-18T22:18:42.082989"}]},{"id":"0584ae87-7118-4531-beaa-46b7010c2cc3","accountNumber":"ACC348059","balance":6920.86,"user":{"id":"6f6abc4d-a3a1-40a0-a98d-8d34d43fc27b","name":"User3"},"transactions":[{"id":"dc170113-f34a-4287-b77c-485544616b75","type":"ACCOUNT_CREATION","amount":6920.86,"timestamp":"2024-10-18T22:18:42.083789"}]}]