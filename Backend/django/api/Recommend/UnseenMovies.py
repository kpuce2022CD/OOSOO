from api.models import Contents
from api.models import ContentsReview
import pandas as pd


def unseen_movies(email, movies):
    contents_id = list()

    review = ContentsReview.objects.filter(u_email=email)

    for seen in review:
        print(seen.c_id)
        contents_id.append(seen.c_id.id)

    seen_contents = Contents.objects.filter(id__in=contents_id).values()

    seen_contents_df = pd.DataFrame(list(seen_contents))
    print(seen_contents_df)

    seen_tmdb = seen_contents_df['id'].to_frame()
    seen_tmdb['tmdbId'] = seen_tmdb.id.str.split('_').str[1]
    seen_tmdb = seen_tmdb['tmdbId'].to_frame()
    print(seen_tmdb)

    movies_tmdb = movies['tmdbId'].to_frame()
    print(movies_tmdb)

    unseen_tmdb = pd.concat([movies_tmdb, seen_tmdb, seen_tmdb]).drop_duplicates(keep=False)
    print(unseen_tmdb)

    return unseen_tmdb.to_dict('list')['tmdbId']
