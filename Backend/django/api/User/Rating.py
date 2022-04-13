import sys
import mariadb
from django.db import connection
from api.models import ContentsReview

def Rating(c_id, u_email, like, rating, review, datetime):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        insert_query = "INSERT INTO contents_review(c_id, u_email, _like, rating, review, _datetime) " \
                       "VALUES(%s, %s, %s, %s, %s, %s) "
        update_query = f"UPDATE contents_review set rating = {rating}, review = '{review}'" \
                       f" WHERE c_id = '{c_id}' AND u_email = '{u_email}'"

        exist = ContentsReview.objects.filter(c_id=c_id)
        exist = exist.filter(u_email=u_email)

        if exist.values().count() != 0:
            cur.execute(use_db_query)
            cur.execute(update_query)
        else:
            cur.execute(use_db_query)
            cur.execute(insert_query, (c_id, u_email, like, rating, review, datetime))

        connection.commit()
        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    connection.close()

    return result