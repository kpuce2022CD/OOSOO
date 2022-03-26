import mariadb
import sys
import pandas as pd


try:
    conn = mariadb.connect(
        user="eoeo0326",
        password="2017150009",
        host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
        port=3306
    )

    cursor = conn.cursor()

    use_db_query = "use oosooDB"
    contents_query = "SELECT * FROM contents"
    cursor.execute(use_db_query)
    cursor.execute(contents_query)
    data = cursor.fetchall()

    contents = pd.DataFrame(data)
    print(contents)


except mariadb.Error as e:
    print(f"Error: {e}")
    sys.exit(1)
