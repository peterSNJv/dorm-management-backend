import base64
from pathlib import Path

from fastapi import APIRouter, HTTPException, Query

from app.models.content import (
    GenerateRequest, GenerateResponse,
    CoverRequest, CoverResponse,
    StockImagesResponse, StockImageItem,
    PublishRequest, PublishResponse,
    HistoryItem, HistoryDetail,
)
from app.services.deepseek import generate_content as generate_content_service
from app.services.stable_diffusion import generate_cover as generate_cover_service
from app.services.image_stock import search_stock_images
from app.services.wechat import upload_material, create_draft, publish

router = APIRouter()

_in_memory_history: list[dict] = []


@router.post("/generate", response_model=GenerateResponse)
async def generate_content(req: GenerateRequest):
    try:
        result = await generate_content_service(req.direction, req.style, req.length)
        return GenerateResponse(**result)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"文案生成失败: {str(e)}")


@router.post("/cover", response_model=CoverResponse)
async def generate_cover(req: CoverRequest):
    try:
        result = await generate_cover_service(req.title, req.keywords, req.style)
        return CoverResponse(**result)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"封面生成失败: {str(e)}")


@router.get("/stock-images", response_model=StockImagesResponse)
async def get_stock_images(
    keywords: str = Query(..., description="搜索关键词，逗号分隔"),
    count: int = Query(default=5, ge=1, le=20),
):
    try:
        keyword_list = [k.strip() for k in keywords.split(",") if k.strip()]
        images = await search_stock_images(keyword_list, count)
        return StockImagesResponse(
            images=[StockImageItem(**img) for img in images]
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"图片搜索失败: {str(e)}")


@router.post("/publish", response_model=PublishResponse)
async def publish_article(req: PublishRequest):
    try:
        cover_path = Path(req.cover_url.lstrip("/"))
        if cover_path.exists():
            cover_data = cover_path.read_bytes()
        else:
            cover_data = base64.b64decode(req.cover_url) if req.cover_url.startswith("data:") else b""

        thumb_media_id = await upload_material(cover_data)
        draft_media_id = await create_draft(req.title, req.content, thumb_media_id, req.summary)
        publish_id = await publish(draft_media_id)

        article = {
            "id": draft_media_id,
            "title": req.title,
            "content": req.content,
            "summary": req.summary,
            "cover_url": req.cover_url,
            "status": "published",
            "created_at": str(int(__import__("time").time())),
        }
        _in_memory_history.append(article)

        return PublishResponse(
            article_id=publish_id or draft_media_id,
            status="published" if publish_id else "draft",
            message="发布成功" if publish_id else "已保存为草稿，请在公众号后台确认发布",
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"发布失败: {str(e)}")


@router.get("/history", response_model=list[HistoryItem])
async def get_history():
    return [
        HistoryItem(
            id=a["id"],
            title=a["title"],
            summary=a.get("summary", ""),
            status=a["status"],
            created_at=a["created_at"],
        )
        for a in reversed(_in_memory_history)
    ]


@router.get("/history/{article_id}", response_model=HistoryDetail)
async def get_history_detail(article_id: str):
    for a in _in_memory_history:
        if a["id"] == article_id:
            return HistoryDetail(**a)
    raise HTTPException(status_code=404, detail="文章不存在")
