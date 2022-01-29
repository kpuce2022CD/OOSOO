import mariadb
import sys

# Connect to MariaDB Platform
try:
    conn = mariadb.connect(
        user="jinho",
        password="2017150011",
        host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
        port=3306
    )

except mariadb.Error as e:
    print(f"failed: Error connecting to Mariadb: {e}")
    sys.exit(1)

#데이터들 임의로 입력
email = "jinho@kpu.ac.kr"
passwd = "123456"
name = "김진호"
phone_number = "010-1111-1111"
nickname = "jinhoho"
gender = True
age = 25
job = "대학생"
overview = "산기대 컴공 김진호입니다."
coin = 10

try:
    cur = conn.cursor()
    use_db_query = "use oosooDB"
    insert_query = "INSERT INTO users(email, passwd, name, phone_number, " \
                   "nickname, gender, age, job, overview, coin) " \
                   "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "

    cur.execute(use_db_query)
    cur.execute(insert_query, (email, passwd, name, phone_number, nickname, gender, age, job, overview, coin))
    conn.commit()

except mariadb.Error as e:
    print(f"Error: {e}")

conn.close()