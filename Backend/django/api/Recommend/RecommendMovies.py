from api.models import Contents


def recommend_movie_list(algo, userId, unseen_movies, movies, top_n=10):
    recommend_list = list()

    # 아직 보지 않은 영화의 예측 평점: prediction 객체 생성
    predictions = []
    for tmdbId in unseen_movies:
        predictions.append(algo.predict(userId, tmdbId))

    # 리스트 내의 prediction 객체의 est를 기준으로 내림차순 정렬
    def sortkey_est(pred):
        return pred.est

    predictions.sort(key=sortkey_est, reverse=True)  # key에 리스트 내 객체의 정렬 기준을 입력

    # 상위 top_n개의 prediction 객체
    top_predictions = predictions[:top_n]

    # 영화 아이디, 제목, 예측 평점 출력
    print(f"Top-{top_n} 추천 영화 리스트")

    for pred in top_predictions:
        tmdb_id = int(pred.iid)
        movie_title = movies[movies["tmdbId"] == tmdb_id]["title"].tolist()[0]
        movie_rating = pred.est

        print(f"{tmdb_id} - {movie_title}: {movie_rating:.2f}")

        db_cid = 'm_' + str(tmdb_id)
        content = Contents.objects.filter(id=db_cid).values()

        recommend_list.append({'recommend': content, 'est_rating': movie_rating})

    return recommend_list
