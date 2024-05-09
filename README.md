SharExpense is a web app that handles your expenses for you. You just provide the details of the expenses and it will calculate the contribution for everyone. SharExpense has groups. Groups consists of members to split the expense. Once the group is created all you need to do is add transactions and it will split the amount appropriately.
The aspect where SharExpense stands out is its ability to scan a receipt and list all the items and prices. You just upload the receipt, SharExpense will show all the items in the receipt and their prices within few seconds. That’s it all you have to do now is provide information on member is participating in which items contribution. SharExpense will calculate the rest and give proper split for every member.
SharExpense uses AWS Textract for parsing the receipts. The entire application is deployed on AWS and uses services like Lambda, RDS, EC2, API Gateway and Load balancer.

SharExpense is developed using Springboot for backend and react frontend. For security purposes JWT has been implemented for user authentication and authorization. The database used is MySQL, but to reduce the complexity a No-SQL database should be used.   

