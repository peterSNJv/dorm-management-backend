import time
import httpx

from app.config import WECHAT_APP_ID, WECHAT_APP_SECRET

_access_token = ""
_token_expires_at = 0


async def _get_access_token() -> str:
    global _access_token, _token_expires_at

    if _access_token and time.time() < _token_expires_at - 60:
        return _access_token

    async with httpx.AsyncClient(timeout=10.0) as client:
        response = await client.get(
            "https://api.weixin.qq.com/cgi-bin/token",
            params={
                "grant_type": "client_credential",
                "appid": WECHAT_APP_ID,
                "secret": WECHAT_APP_SECRET,
            },
        )
        response.raise_for_status()

    data = response.json()
    if "access_token" not in data:
        raise Exception(f"获取 access_token 失败: {data}")

    _access_token = data["access_token"]
    _token_expires_at = time.time() + data.get("expires_in", 7200)
    return _access_token


async def upload_material(image_data: bytes, media_type: str = "image") -> str:
    token = await _get_access_token()

    async with httpx.AsyncClient(timeout=30.0) as client:
        response = await client.post(
            f"https://api.weixin.qq.com/cgi-bin/material/add_material",
            params={"access_token": token, "type": media_type},
            files={"media": ("image.png", image_data, "image/png")},
        )
        response.raise_for_status()

    data = response.json()
    if "media_id" not in data:
        raise Exception(f"上传素材失败: {data}")

    return data["media_id"]


async def create_draft(title: str, content: str, thumb_media_id: str, digest: str = "") -> str:
    token = await _get_access_token()

    articles = {
        "articles": [
            {
                "title": title,
                "author": "",
                "digest": digest,
                "content": content,
                "thumb_media_id": thumb_media_id,
                "need_open_comment": 0,
                "only_fans_can_comment": 0,
            }
        ]
    }

    async with httpx.AsyncClient(timeout=30.0) as client:
        response = await client.post(
            f"https://api.weixin.qq.com/cgi-bin/draft/batchadd",
            params={"access_token": token},
            json=articles,
        )
        response.raise_for_status()

    data = response.json()
    if "media_id" not in data:
        raise Exception(f"创建草稿失败: {data}")

    return data["media_id"]


async def publish(draft_media_id: str) -> str:
    token = await _get_access_token()

    async with httpx.AsyncClient(timeout=30.0) as client:
        response = await client.post(
            f"https://api.weixin.qq.com/cgi-bin/freepublish/submit",
            params={"access_token": token},
            json={"media_id": draft_media_id},
        )
        response.raise_for_status()

    data = response.json()
    if data.get("errcode", 0) != 0:
        raise Exception(f"发布失败: {data}")

    return data.get("publish_id", "")
