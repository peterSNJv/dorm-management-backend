import base64
import uuid
from pathlib import Path

import httpx

from app.config import SD_WEBUI_URL

COVERS_DIR = Path("static/covers")
COVERS_DIR.mkdir(parents=True, exist_ok=True)


async def generate_cover(title: str, keywords: list[str], style: str = "realistic") -> dict:
    keyword_text = "、".join(keywords[:3]) if keywords else title
    prompt = f"公众号封面图，主题：{title}，关键词：{keyword_text}，风格：{style}，高清，16:9 比例，专业设计感，简洁大气"
    negative_prompt = "low quality, blurry, text, watermark, logo"

    async with httpx.AsyncClient(timeout=120.0) as client:
        response = await client.post(
            f"{SD_WEBUI_URL}/sdapi/v1/txt2img",
            json={
                "prompt": prompt,
                "negative_prompt": negative_prompt,
                "width": 1024,
                "height": 576,
                "steps": 20,
                "cfg_scale": 7,
                "batch_size": 1,
            },
        )
        response.raise_for_status()

    result = response.json()
    image_base64 = result["images"][0]

    filename = f"cover_{uuid.uuid4().hex[:8]}.png"
    filepath = COVERS_DIR / filename
    filepath.write_bytes(base64.b64decode(image_base64))

    return {
        "image_url": f"/static/covers/{filename}",
        "image_data": image_base64,
    }
