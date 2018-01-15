--CPU
select stat_name, value, comments from v$osstat
where stat_name in ('IDLE_TIME', 'NUM_CPUS', 'BUSY_TIME', 'USER_TIME',
                    'IOWAIT_TIME', 'NICE_TIME', 'NUM_CPU_CORES');
select * from cpu;

--Memória
select sum(value)/1024/1024 "Values" from v$sga
union all
select round((sum(pga_used_mem)/1024/1024),1) from v$process;
select * from memory;


--Datafiles
(SELECT  Substr(df.tablespace_name,1,20) "Tablespace Name",
        Substr(df.file_name,1,80) "File Name",
        Round(df.bytes/1024/1024,2) "Size (M)",
        decode(e.used_bytes,NULL,0,Round(e.used_bytes/1024/1024,2)) "Used (M)",
        decode(e.used_bytes,NULL,0,Round((e.used_bytes/df.bytes)*100,2)) "% Used"
FROM    DBA_DATA_FILES DF,
       (SELECT file_id, sum(bytes) used_bytes FROM dba_extents
        GROUP by file_id) E,
       (SELECT Max(bytes) free_bytes, file_id FROM dba_free_space
        GROUP BY file_id) f
WHERE    e.file_id (+) = df.file_id AND df.file_id  = f.file_id (+))
UNION ALL SELECT  TABLESPACE_NAME "TableSpace Name",
    FILE_NAME "File Name", round(BYTES/1024/1024,2) "SIZE (M)",
    round((BYTES/1024/1024)-(USER_BYTES/1024/1024),2) "USED (M)",
    round((((BYTES/1024/1024)-(USER_BYTES/1024/1024))/(BYTES/1024/1024))*100,2) "% USED"
    from dba_temp_files;
select * from datafiles;


--Sessão
select sid, substr(b.machine,1,15) box, substr(b.username,1,10) username,
       substr(b.osuser,1,8) os_user, substr(b.program,1,28) program
from v$session b, v$process a
where b.paddr = a.addr
order by sid asc;
select * from sessions;


--Permissões
select * from DBA_SYS_PRIVS order by GRANTEE;
select * from grants;


--Utilizador
select username, default_tablespace, temporary_tablespace, last_login
from dba_users;
select * from users;


--Tablespaces
SELECT  a.tablespace_name tablespace,
        ROUND(((c.BYTES-NVL(b.BYTES,0))/c.BYTES)*100,2) "Pct. Used",
        c.BYTES/1024/1024 "Total MB", ROUND(c.BYTES/1024/1024-NVL(b.BYTES,0)/1024/1024,2) "Used MB",
        ROUND(NVL(b.BYTES,0)/1024/1024,2) "Free MB", c.DATAFILES
FROM dba_tablespaces a,
(    SELECT   tablespace_name, SUM(BYTES) BYTES
     FROM   dba_free_space
     GROUP BY tablespace_name) b,
(    SELECT COUNT(1) DATAFILES, SUM(BYTES) BYTES, tablespace_name
     FROM   dba_data_files
     GROUP BY   tablespace_name) c
WHERE b.tablespace_name(+) = a.tablespace_name AND
      c.tablespace_name(+) = a.tablespace_name
ORDER BY NVL(((c.BYTES-NVL(b.BYTES,0))/c.BYTES),0) DESC;
select * from tablespaces;


--Tabelas
select owner as "Schema", segment_name as "Object Name",
       round(bytes/1024/1024,2) as "Object Size (Mb)",
       tablespace_name as "Tablespace"
from dba_segments
where segment_type = 'TABLE'
order by owner;
select * from tables;
