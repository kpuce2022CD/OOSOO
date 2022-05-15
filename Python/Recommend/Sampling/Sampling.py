import mariadb
import sys
#import pandas as pd
import numpy as np

conn = mariadb.connect(
    user="eoeo0326",
    password="2017150009",
    host="oosoords.cqo7bp08tx6h.ap-northeast-2.rds.amazonaws.com",
    port=3306,
    db="oosooDB"
)

cursor = conn.cursor(dictionary=True)

contents_query = "SELECT * FROM contents ORDER BY vote_count DESC"
cursor.execute(contents_query)
data = cursor.fetchall()

np.random.seed(326)

sigma = 0.5  # 표준편자
size = 0

#dt_ratings = pd.DataFrame(columns = ['content_id', 'rating'])
#dict_ratings = {}
list_ratings = list()

for content in data:
    count = int(content["vote_count"])

    if count != 0:
        print("id = " + str(content["id"]) +
          ",  title = " + str(content["title"]) +
          ",  vote_count = " + str(content["vote_count"]) +
          ",  vote_average = " + str(content["vote_average"]))

        avg = content["vote_average"]
        c_id = content["id"]
        size += count

        rand_norm = np.random.normal(avg, sigma, size=count)
        sample = np.clip(rand_norm, 0, 10)

        for rating in sample:
            dict_rating = {'content_id' : str(c_id), 'rating' : rating}
            list_ratings.append(dict_rating)

print('list_len : ' + str(len(list_ratings)))
print('size : ' + str(size))

print(list_ratings[0])
