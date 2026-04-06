import type { CandidateRequest, CandidateResponse } from '../types'

const BASE_URL = 'http://localhost:8080/api/candidates';

export const candidateService = {
    async searchByName(name: string): Promise<CandidateResponse[]> {
        const res = await fetch(`${BASE_URL}/search/by-name?name=${name}`);
        return res.json();
    },

    async searchByMultipleSkills(skills: string[]): Promise<CandidateResponse[]> {
        const params = new URLSearchParams();
        skills.forEach(s => params.append('skill', s));
        const res = await fetch(`${BASE_URL}/search/by-skills?${params.toString()}`);
        return res.json();
    },

    async searchBySingleSkill(skill: string): Promise<CandidateResponse[]> {
        const res = await fetch(`${BASE_URL}/search/by-skill?skill=${encodeURIComponent(skill)}`);
        return res.json();
    },

    async create(candidate: CandidateRequest): Promise<CandidateResponse> {
        const res = await fetch(BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(candidate)
        });
        return res.json();
    },

    async delete(id: number): Promise<void> {
        await fetch(`${BASE_URL}/${id}`, { method: 'DELETE' });
    },

    async addSkill(id: number, skill: string): Promise<CandidateResponse> {
        const res = await fetch(`${BASE_URL}/${id}/skills?skill=${skill}`, { method: 'POST' });
        return res.json();
    },

    async removeSkill(id: number, skill: string): Promise<CandidateResponse> {
        const res = await fetch(`${BASE_URL}/${id}/skills?skill=${skill}`, { method: 'DELETE' });
        return res.json();
    }
};