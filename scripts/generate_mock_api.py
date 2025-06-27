import sys

TEMPLATE = '''
@GetMapping("/{api}")
public Map<String, Object> {api}() {{
    Map<String, Object> resp = new HashMap<>();
    resp.put("mock", "{api} response");
    return resp;
}}
'''

def main():
    apis = sys.argv[1:]
    for api in apis:
        print(TEMPLATE.format(api=api))

if __name__ == "__main__":
    main() 