<%-- 
    Document   : home
    Created on : Sep 10, 2018, 2:17:14 PM
    Author     : pramod_d
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task App</title>
        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            form {border: 5px solid #ccffcc;}
            h2   {width: 20%;padding-left:45%}

            input[type=text], input[type=number] {
                width: 100%;
                padding: 12px 25px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            button {
                background-color: #4CAF50;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
            }

            button:hover {
                opacity: 0.8;
            }

            .cancelbtn {
                width: auto;
                padding: 10px 18px;
                background-color: #f44336;
            }

            .imgcontainer{
                text-align: center;
                margin: 20px 0 12px 0;
            }

            image.avatar {
                width: 5%;
                border-radius: 50%;
            }

            .container {
                width: 20%;
                padding-left:40%
            }

            span.psw {
                float: right;
                padding-top: 16px;
            }

            /* Change styles for span and cancel button on extra small screens */
            @media screen and (max-width: 300px) {
                span.psw {
                    display: block;
                    float: none;
                }
                .cancelbtn {
                    width: 100%;
                }
            }
            div{
                border-radius: 5px;
                background-color: #ccffcc;
                padding: 10px;
            }
        </style>
        <script>
            function myFunction() {
                if (window.confirm("Are you Sure?")) {
                   continue;
                }
            }
        </script>
    </head>
    <body>
        <h1>Welcome ${username} to Your e-Wallet!</h1>
        <form method="POST" action="withdraw">
            <div>
                <div class="container">
                    <label for="balance"><b>Account Balance: ${initial_amount}/=</b></label>

                    <input type="number" placeholder="Enter an amount to withdraw" name="withdraw_amount">
                    <input type="Hidden" name="uname" value="${username}">
                    <button type="submit" onclick="confirm()">Withdraw</button>
                </div>
            </div>
        </form>
    </body>
</html>
