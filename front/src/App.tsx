import { useState, useEffect } from 'react';
import type {CandidateResponse} from './types';
import { candidateService } from './api/candidateService';
import CandidateList from './components/CandidateList';
import CandidateForm from './components/CandidateForm';
import CandidateSearch from './components/CandidateSearch';

function App() {
    const [candidates, setCandidates] = useState<CandidateResponse[]>([]);

    const loadAll = async () => {
        const data = await candidateService.searchByName('');
        setCandidates(data);
    };

    useEffect(() => {
        loadAll();
    }, []);

    return (
        <div style={{ maxWidth: '800px', margin: '0 auto', fontFamily: 'sans-serif' }}>
            <h1 style={{marginBottom: 0}}>HR Candidate Management Portal</h1>
            <h6 style={{margin: 0}}>aka the promising talent list!</h6>

            <CandidateForm onCreated={loadAll} />

            <hr />

            <CandidateSearch onSearchResults={(results) => setCandidates(results)} />

            <CandidateList candidates={candidates} refreshList={loadAll} />
        </div>
    );
}

export default App;