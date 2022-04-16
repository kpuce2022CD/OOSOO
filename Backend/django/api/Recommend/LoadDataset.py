import pandas as pd


def load_dataset():
    # 데이터셋 로딩
    # Docker : '/webserver/src/api/Recommend/ml-dataset/~~'
    # Pycharm : r'D:\2022 Capston\OOSOO\Backend\django\api\Recommend\ml-dataset\~~'
    ratings = pd.read_csv('/webserver/src/api/Recommend/ml-dataset/ratings_tmdb.csv')
    movies = pd.read_csv('/webserver/src/api/Recommend/ml-dataset/movies_tmdb.csv')
    #links = pd.read_csv('/webserver/src/api/Recommend/ml-dataset/links.csv')

    print(ratings)
    print(movies)
    print(ratings.dtypes)
    print(movies.dtypes)
    #print(links)

    return ratings, movies
