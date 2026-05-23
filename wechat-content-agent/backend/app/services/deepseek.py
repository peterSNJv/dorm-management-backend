import json
import httpx

from app.config import DEEPSEEK_API_KEY, DEEPSEEK_BASE_URL

SYSTEM_PROMPT = """你是一个专业的公众号内容创作者。请根据用户要求生成文章。

请返回以下 JSON 格式（不要包含 markdown 代码块标记）：
{
  "title": "文章标题（15-25字，吸引眼球）",
  "content": "文章正文（Markdown 格式）",
  "summary": "文章摘要（50-100字）",
  "keywords": ["关键词1", "关键词2", "关键词3", "关键词4"]
}

注意事项：
- 标题要吸引人，适合公众号传播
- 正文结构清晰，适当使用小标题
- 摘要要概括核心观点
- 关键词用于后续搜索配图，选择视觉相关的词"""


async def generate_content(direction: str, style: str, length: int) -> dict:
    user_message = f"主题方向：{direction}\n文章风格：{style}\n目标字数：{length}"

    async with httpx.AsyncClient(timeout=60.0) as client:
        response = await client.post(
            f"{DEEPSEEK_BASE_URL}/v1/chat/completions",
            headers={
                "Authorization": f"Bearer {DEEPSEEK_API_KEY}",
                "Content-Type": "application/json",
            },
            json={
                "model": "deepseek-chat",
                "messages": [
                    {"role": "system", "content": SYSTEM_PROMPT},
                    {"role": "user", "content": user_message},
                ],
                "response_format": {"type": "json_object"},
                "temperature": 0.7,
            },
        )
        response.raise_for_status()

    result = response.json()
    content = result["choices"][0]["message"]["content"]
    return json.loads(content)
