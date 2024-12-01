/**
 * 生成用户头像
 * @param {string} userName 用户名
 * @returns {string} 头像的 SVG 数据 URL
 */
export const generateAvatar = (userName) => {
    const firstChar = userName.charAt(0).toUpperCase();
    return `data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="80" height="80"><rect width="100%" height="100%" fill="white"/><text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" font-size="36" fill="black">${firstChar}</text></svg>`;
};