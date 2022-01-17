from rest_framework import serializers
from .models import *


class ContentsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contents        # Contents 모델 사용
        fields = '__all__'      # 모든 필드 포함


class ContentsReviewSerializer(serializers.ModelSerializer):
    class Meta:
        model = ContentsReview  # ContentsReview 모델 사용
        fields = '__all__'      # 모든 필드 포함


class InterworkingSerializer(serializers.ModelSerializer):
    class Meta:
        model = Interworking    # Interworking 모델 사용
        fields = '__all__'      # 모든 필드 포함


class JjimSerializer(serializers.ModelSerializer):
    class Meta:
        model = Jjim            # Jjim 모델 사용
        fields = '__all__'      # 모든 필드 포함


class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users           # Users 모델 사용
        fields = '__all__'      # 모든 필드 포함


class WatchingLogSerializer(serializers.ModelSerializer):
    class Meta:
        model = WatchingLog     # WatchingLog 모델 사용
        fields = '__all__'      # 모든 필드 포함
