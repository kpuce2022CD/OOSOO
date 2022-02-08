import mariadb
import sys

def signUp(email, pwd, name, phone_num, nickname, gender, age, job, overview):

    try:
        conn = mariadb.connect(
            user="jinho",
            password="2017150011",
            host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
            port=3306
        )

        cur = conn.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO users(email, passwd, name, phone_number, " \
                       "nickname, gender, age, job, overview) " \
                       "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) "

        cur.execute(use_db_query)
        cur.execute(insert_query, (email, pwd, name, phone_num, nickname, gender, age, job, overview))
        conn.commit()

        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    conn.close()

    return result

