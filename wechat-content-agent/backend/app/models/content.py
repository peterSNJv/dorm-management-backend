from pydantic import BaseModel, Field


class GenerateRequest(BaseModel):
    direction: str = Field(..., description="文章方向")
    style: str = Field(default="科普", description="文章风格")
    length: int = Field(default=1000, ge=200, le=5000, description="目标字数")


class GenerateResponse(BaseModel):
    title: str
    content: str
    summary: str
    keywords: list[str]


class CoverRequest(BaseModel):
    title: str = Field(..., description="文章标题")
    keywords: list[str] = Field(default_factory=list)
    style: str = Field(default="realistic", description="图片风格")


class CoverResponse(BaseModel):
    image_url: str
    image_data: str


class StockImageItem(BaseModel):
    url: str
    thumbnail: str
    photographer: str


class StockImagesResponse(BaseModel):
    images: list[StockImageItem]


class PublishRequest(BaseModel):
    title: str
    content: str
    summary: str
    cover_url: str
    images: list[str] = Field(default_factory=list)


class PublishResponse(BaseModel):
    article_id: str
    status: str
    message: str


class HistoryItem(BaseModel):
    id: str
    title: str
    summary: str
    status: str
    created_at: str


class HistoryDetail(BaseModel):
    id: str
    title: str
    content: str
    summary: str
    cover_url: str
    status: str
    created_at: str
