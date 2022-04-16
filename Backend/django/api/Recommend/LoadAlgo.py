import os
from surprise import dump


def load_algo():
    # 모델 로딩
    # Docker : '/webserver/src/api/Recommend/SurpriseSVD'
    # Pycharm : r'D:\2022 Capston\OOSOO\Backend\django\api\Recommend\SurpriseSVD'
    file_name = os.path.expanduser('/webserver/src/api/Recommend/SurpriseSVD')
    _, algo_loaded = dump.load(file_name)

    uid = 9
    iid = 326

    # 추천 예측 평점 (.predict)
    pred_loaded = algo_loaded.predict(uid, iid)
    print(pred_loaded)

    return algo_loaded
