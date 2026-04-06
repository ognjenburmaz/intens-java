export interface CandidateRequest {
    fullName: string;
    dateOfBirth: string;
    contactNumber: string;
    email: string;
    skills: string[];
}

export interface CandidateResponse {
    id: number;
    fullName: string;
    dateOfBirth: string;
    contactNumber: string;
    email: string;
    skills: string[];
}