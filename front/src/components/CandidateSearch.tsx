import { useState } from 'react';
import { candidateService } from '../api/candidateService';
import type {CandidateResponse} from '../types';

interface Props {
    onSearchResults: (results: CandidateResponse[]) => void;
}

export default function CandidateSearch({ onSearchResults }: Props) {
    const [query, setQuery] = useState('');

    const handleSearchByName = async () => {
        const data = await candidateService.searchByName(query);
        onSearchResults(data);
    };

    const handleSkillSearch = async () => {
        const skillArray = query
            .split(',')
            .map(s => s.trim())
            .filter(s => s !== '');

        if (skillArray.length === 0) return;

        let results: CandidateResponse[];

        if (skillArray.length === 1) {
            results = await candidateService.searchBySingleSkill(skillArray[0]);
        } else {
            results = await candidateService.searchByMultipleSkills(skillArray);
        }

        onSearchResults(results);
    };

    return (
        <fieldset>
            <legend>Search Candidates</legend>
            <input
                type="text"
                placeholder="Name or Skill(s) (comma separated)..."
                style={{ width: '300px' }}
                value={query}
                onChange={(e) => setQuery(e.target.value)}
            />
            <div style={{ marginTop: '10px' }}>
                <button onClick={handleSearchByName}>Search by Name</button>
                <button onClick={handleSkillSearch}>Search by Skill(s)</button>
                <button onClick={() => { setQuery(''); onSearchResults([]); }}>Clear Results</button>
            </div>
            <p style={{ fontSize: '0.8rem', color: 'gray' }}>
                Tip: Use commas for multiple skills (e.g. "java, sql, react")
            </p>
        </fieldset>
    );
}