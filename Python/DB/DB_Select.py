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


use_db_query = "use oosooDB"
select_movie_query = "SELECT id, title, genre, release_date, vote_average FROM contents WHERE _type = 'movie'; "
select_tv_query = "SELECT id, title, genre, number_of_seasons, number_of_episodes, vote_average FROM contents WHERE _type = 'tv'; "
select_user_query = "SELECT * FROM users"

while True:
    print("")
    print("조회하실 컨텐츠를 골라주세요 ")
    print("1 영화")
    print("2 TV 프로그램")
    print("3 User 정보")
    print("4 종료")
    a = input("입력: ")

    if a == '1':
        # 영화 정보에서 아이디, 타이틀, 장르, 출시일, 평점 조회
        try:
            cur = conn.cursor()

            cur.execute(use_db_query)
            cur.execute(select_movie_query)
            result_set_movie = cur.fetchall()

            print("select 타이틀, 장르, 출시일 ")
            for id, title, genre, release_date, vote_average in result_set_movie:
                print(f"id: {id},  title: {title},  genre: {genre},  release_date: {release_date}, vote_average: {vote_average}")

        except mariadb.Error as e:
            print(f"Error: {e}")

    elif a == '2':
        # TV 정보에서 아이디, 타이틀, 장르, 시즌 수, 에피소드 수, 평점 조회
        try:
            cur = conn.cursor()

            cur.execute(use_db_query)
            cur.execute(select_tv_query)
            result_set_tv = cur.fetchall()

            print("select 타이틀, 장르, 출시일 ")
            for id, title, genre, number_of_seasons, number_of_episodes, vote_average in result_set_tv:
                print(f"id: {id},  title: {title},  genre: {genre},  시즌 수 : {number_of_seasons},  에피소드 수: {number_of_episodes}, vote_average: {vote_average}")

        except mariadb.Error as e:
            print(f"Error: {e}")

    elif a == '3':
        #유저 정보 조회
        try:
            cur = conn.cursor()

            cur.execute(use_db_query)
            cur.execute(select_user_query)
            result_set_tv = cur.fetchall()

            print("유저 정보 조회")
            for id, email, passwd, name, phone_number, nickname, gender, age, job, overview, coin in result_set_tv:
                if gender == True:
                    str_gender = "남성"
                else:
                    str_gender = "여성"
                print(f"id: {id},  email: {email},  password: {passwd},  이름 : {name},  핸드폰 번호: {phone_number},"
                      f" 닉네임: {nickname}, 성별: {str_gender}, 나이: {age}, 직업: {job}, 설명: {overview}, coin: {coin}")

        except mariadb.Error as e:
            print(f"Error: {e}")

    else:
        # 연결 닫기
        conn.close()
        print("종료")
        break

